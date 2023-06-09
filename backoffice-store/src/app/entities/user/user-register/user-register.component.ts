import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AuthService } from '../auth.service';
import { User } from '../model/user.model';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.scss']
})
export class UserRegisterComponent {
  registerForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router,
    private authService : AuthService
  ) {
    this.registerForm = this.fb.group({
      nick: [
        '',
        [Validators.required],
        [(control: AbstractControl) => this.checkNickExists(control)]
      ],
      nombre: ['', [Validators.required]],
      apellidos: ['', [Validators.required]],
      telefono: ['', [Validators.required, Validators.pattern(/^\d+$/)]],
      email: [
        '',
        [Validators.required, Validators.email],
        [(control: AbstractControl) => this.checkEmailExists(control)]
      ],
      password: ['', [Validators.required]],
      confirmPassword: ['', [Validators.required]]
    }, { validators: this.passwordMatchValidator });
  }

  

  registerUser(): void {
    if (this.registerForm.valid) {
      const user = this.registerForm.value as User;
      this.userService.registerUser(user).subscribe(
        (response) => {
          if (response.body && response.body.id !== undefined) {
            const sessionId = response.body.sessionId;
            if (sessionId) {
              localStorage.setItem('sessionId', sessionId);
            }
      
            localStorage.setItem('user', JSON.stringify(response.body));
            localStorage.setItem('id', response.body.id.toString());
            this.router.navigate(['users', response.body.id]);

            
            

          }
        },
        (error) => {
          console.error('Error en el registro:', error);
          // Manejar el error aquí
        }
      );
    }
  }
  
  passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const password = control.get('password')?.value;
    const confirmPassword = control.get('confirmPassword')?.value;
  
    if (password && confirmPassword && password !== confirmPassword) {
      return { passwordMismatch: true };
    }
  
    return null;
  }

  // Método para verificar si el email ya existe
  checkEmailExists(control: AbstractControl): Observable<ValidationErrors | null> {
    const email = control.value;
    return this.userService.checkEmailExists(email).pipe(
      map((exists: boolean) => (exists ? { emailExists: true } : null))
    );
  }

  // Método para verificar si el nick ya existe
  checkNickExists(control: AbstractControl): Observable<ValidationErrors | null> {
    const nick = control.value;
    return this.userService.checkNickExists(nick).pipe(
      map((exists: boolean) => (exists ? { nickExists: true } : null))
    );
  }
}
