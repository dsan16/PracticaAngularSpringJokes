import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Categories } from '../model/categories.model';

@Injectable({
  providedIn: 'root'
})
export class CategoriesService {
  private url = 'http://localhost:8080/api/categories';

  constructor(private http: HttpClient) {}

  getCategories(): Observable<Categories[]> {
    return this.http.get<Categories[]>(this.url);
  }

  createCategory(category: Categories): Observable<Categories> {
    return this.http.post<Categories>(this.url, category);
  }

  updateCategory(category: Categories): Observable<Categories> {
    return this.http.put<Categories>(`${this.url}/${category.id}`, category);
  }

  deleteCategory(id: number): Observable<any> {
    return this.http.delete(`${this.url}/${id}`);
  }
}
