import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { catchError, Observable, retry, throwError } from "rxjs";

export class HttpRequestIntercept implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const sessionId = localStorage.getItem('sessionId');
    
        if (sessionId) {
          // Clona la solicitud y agrega el token a las cabeceras
            const modifiedReq = req.clone({
            setHeaders: {
                Authorization: sessionId
            }
            });
        
        return next.handle(modifiedReq)
            .pipe(
            retry(3),
            catchError((error: HttpErrorResponse) => {
                let errorMessage = '';
                
                if (error.status) {
                errorMessage = `Error Status: ${error.status}\nMessage: ${error.message}`;
                } else {
                errorMessage = `Error: ${error.message}`;
                }
                
                console.log(errorMessage);
                return throwError(error);
            })
            );
        } else {
        // Continúa con la solicitud original si no hay token
        return next.handle(req);
        }
    }
}