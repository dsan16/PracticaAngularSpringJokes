import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PrimeraVez } from '../model/primeraVez.model';

@Injectable({
  providedIn: 'root'
})
export class PrimeraVezService {
  private url = 'http://localhost:8080/api/primera_vez';

  constructor(private http: HttpClient) {}

  getAll(): Observable<PrimeraVez[]> {
    return this.http.get<PrimeraVez[]>(this.url);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }

  create(primeraVez: PrimeraVez): Observable<PrimeraVez> {
    return this.http.post<PrimeraVez>(this.url, primeraVez);
  }

  update(primeraVez: PrimeraVez): Observable<PrimeraVez> {
    return this.http.put<PrimeraVez>(`${this.url}/${primeraVez.id}`, primeraVez);
  }

  getPrimeraVezById(id: number): Observable<PrimeraVez> {
    return this.http.get<PrimeraVez>(`${this.url}/${id}`);
  }
}
