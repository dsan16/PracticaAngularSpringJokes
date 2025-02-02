import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Language } from '../model/language.model';

@Injectable({
  providedIn: 'root'
})
export class LanguagesService {
  private url = 'http://localhost:8080/api/languages';

  constructor(private http: HttpClient) {}

  getLanguages(): Observable<Language[]> {
    return this.http.get<Language[]>(this.url);
  }

  createLanguage(language: Language): Observable<Language> {
    return this.http.post<Language>(this.url, language);
  }

  updateLanguage(language: Language): Observable<Language> {
    return this.http.put<Language>(`${this.url}/${language.id}`, language);
  }

  deleteLanguage(id: number): Observable<any> {
    return this.http.delete(`${this.url}/${id}`);
  }
}
