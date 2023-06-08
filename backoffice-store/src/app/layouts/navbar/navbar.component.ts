import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/entities/user/auth.service';
import { UserService } from 'src/app/entities/user/service/user.service';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  isLoggedIn = false;
  nick: string = "";
  isLoginFormVisible = false;
  userId: number | undefined;


  loginForm!: FormGroup;
  error: string ="";

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private  userService: UserService,  private router: Router) {
    this.userId =this.userService.getUserIdFromLocalStorage();
  }


  ngOnInit(): void {
    this.userId = this.userService.getUserIdFromLocalStorage();
    const sessionId = localStorage.getItem('sessionId');
    if (sessionId) {
      this.isLoggedIn = true;
      this.getNickFromService();
    }

    this.loginForm = this.formBuilder.group({
      nick: [''],
      password: ['']
    });
  }

  logout(): void {
    this.authService.logout();
    this.isLoggedIn = false;
    this.userId = this.userService.getUserIdFromLocalStorage();
  }

  getNickFromService(): void {
    this.nick = this.userService.getNickFromLocalStorage();
  }

  showLoginForm(): void {
    this.isLoginFormVisible = true;
  }

  hideLoginForm(): void {
    this.isLoginFormVisible = false;
  }

  login(): void {
    if (this.loginForm.valid) {
      const nickControl = this.loginForm.get('nick');
      const passwordControl = this.loginForm.get('password');

      if (nickControl && passwordControl) {
        const nick = nickControl.value;
        const password = passwordControl.value;

        this.authService.login(nick, password).subscribe({
          next: () => {
            this.isLoggedIn = true;
            this.hideLoginForm();
            this.getNickFromService();

            const userId = this.userService.getUserIdFromLocalStorage();
            if (userId) {
              this.router.navigate(['/users', userId]); // Navegar a la ruta del perfil del usuario
            }
          },
          error: (error) => {
            this.error = 'Credenciales inválidas. Por favor, inténtalo nuevamente.';
            console.log(error);
          }
        });
      }
    }
  }
}
