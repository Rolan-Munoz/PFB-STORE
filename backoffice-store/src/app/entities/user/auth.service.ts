import { Injectable } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BehaviorSubject, catchError, map, Observable, switchMap, tap, throwError } from 'rxjs';
import { UserService } from './service/user.service';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  loginForm: FormGroup;
  error: string = '';
  isLoginFormVisible: boolean = false;



  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router,
  ) {
    this.loginForm = this.fb.group({
      nick: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  login(nick: string, password: string): Observable<any> {
    const user = { nick, password };
  
    return this.userService.loginUser(user).pipe(
      switchMap((response) => {
        if (response.sessionId && response.id !== undefined) {
          localStorage.setItem('sessionId', response.sessionId);

          localStorage.setItem('id', response.id.toString());
  
          return this.userService.getUserById(response.id).pipe(
            tap((userData) => {
              localStorage.setItem('user', JSON.stringify(userData));
            }),
            map(() => {
              this.router.navigateByUrl('/');
              this.isLoginFormVisible = false;
            })
          );
        } else {
          this.error = 'Error de sesión. Por favor, inténtalo nuevamente.';
          return throwError(this.error);
        }
      }),
      catchError((error) => {
        this.error = 'Credenciales inválidas. Por favor, inténtalo nuevamente.';
        return throwError(error);
      })
    );
  }

  
  
  

  logout(): void {
    this.userService.logoutUser().subscribe(
      () => {
        localStorage.removeItem('sessionId');
        localStorage.removeItem('user');
        localStorage.clear();
        this.router.navigateByUrl('/');
      },
      (error) => {
        console.error('Error al cerrar sesión:', error);
      }
    );
  }
} 

  
  
