import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, ValidationErrors, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from '../model/user.model';
import { UserService } from '../service/user.service';


@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.scss']
})
export class UserRegisterComponent {
  registerForm = this.fb.group({
    nick: [
      '',
      Validators.required,
      (control: AbstractControl) => this.checkNickExists(control)
    ],
    nombre: ['', Validators.required],
    apellidos: ['', Validators.required],
    telefono: ['', Validators.required],
    email: [
      '',
      [Validators.required, Validators.email],
      (control: AbstractControl) => this.checkEmailExists(control)
    ],
    password: ['', Validators.required],
    confirmPassword: ['', Validators.required]

  });

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {}

  registerUser(): void {
    if (this.registerForm.valid) {
      const user = this.registerForm.value as User;
      this.userService.registerUser(user).subscribe(
        (response) => {
          if (response.body && response.body.id !== undefined) {
            // Guardar el token de sesión y la información del usuario en el almacenamiento local
            const sessionId = response.body.sessionId;
            if (sessionId) {
              localStorage.setItem('sessionId', sessionId);
            } else {
              // Manejar el caso cuando sessionId es undefined
            }
      
            localStorage.setItem('user', JSON.stringify(response.body));
      
            // Guardar el ID del usuario en el localStorage
            localStorage.setItem('id', response.body.id.toString());
      
            // Redirigir a la página de perfil del usuario
            this.router.navigate(['users', response.body.id]);
          }
        },
        (error) => {
          // Error en el registro
          console.error('Error en el registro:', error);
          // Manejar el error aquí
        }
      );
      
      
    }
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
