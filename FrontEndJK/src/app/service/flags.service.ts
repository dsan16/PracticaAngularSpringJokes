import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Flags } from '../model/flags.model';

@Injectable({
  providedIn: 'root'
})
export class FlagsService {
  private url = 'http://localhost:8080/api/flags';

  constructor(private http: HttpClient) {}

  getFlags(): Observable<Flags[]> {
    return this.http.get<Flags[]>(this.url);
  }

  updateFlag(flag: Flags): Observable<Flags> {
    return this.http.put<Flags>(`${this.url}/${flag.id}`, flag);
  }

  createFlag(flag: Flags): Observable<Flags> {
    return this.http.post<Flags>(this.url, flag);
  }

  deleteFlag(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
