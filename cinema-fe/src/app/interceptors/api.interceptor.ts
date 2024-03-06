import { HttpInterceptorFn } from '@angular/common/http';

export const apiInterceptor: HttpInterceptorFn = (req, next) => {
  const modifiedRequest = req.clone({
    url: req.url.replace('https://', 'http://'),
  });
  // Passa la richiesta modificata al gestore successivo
  return next(modifiedRequest);
};
