import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Types } from '../model/types.model';

@Injectable({
  providedIn: 'root'
})
export class TypesService {
  private url = 'http://localhost:8080/api/types';

  constructor(private http: HttpClient) {}

  getTypes(): Observable<Types[]> {
    return this.http.get<Types[]>(this.url);
  }

  createType(type: Types): Observable<Types> {
    return this.http.post<Types>(this.url, type);
  }

  updateType(type: Types): Observable<Types> {
    return this.http.put<Types>(`${this.url}/${type.id}`, type);
  }

  deleteType(id: number): Observable<any> {
    return this.http.delete(`${this.url}/${id}`);
  }
}
