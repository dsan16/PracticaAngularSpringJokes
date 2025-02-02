import { Component, ViewChild } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Jokes } from '../../model/jokes.model';
import { MatIcon } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatPaginator } from '@angular/material/paginator';
import { JokesService } from '../../service/jokes.service';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-jokes-television-list',
  standalone: true,
  imports: [MatTableModule, FormsModule, CommonModule, MatPaginator],
  templateUrl: './jokes-television-list.component.html',
  styleUrl: './jokes-television-list.component.css'
})
export class JokesTelevisionListComponent {

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  displayedColumns: string[] = ['id', 'language', 'text', 'category', 'flags', 'type', 'primeraVez', 'telefonos'];
  jokes = new MatTableDataSource<Jokes>();
  searchText: string = '';

  constructor(private jokesService: JokesService, private router: Router) {}

  ngOnInit(): void {
    this.cargarJokes();

    this.jokes.filterPredicate = (data: Jokes, filter: string) => {
      if (!data.text1) return false;
      return data.text1.toLowerCase().includes(filter);
    };
  }

  cargarJokes(): void {
    this.jokesService.getJokes().subscribe(jokes => {
      this.jokes.data = jokes;
      this.jokes.paginator = this.paginator;
    });
  }

  openJokes(): void {
    this.router.navigate(['/jokes']).then(() => {
      window.location.reload();
    });
  }

  getFlags(joke: Jokes): string {
    if (!joke.flagses || joke.flagses.length === 0) {
      return 'Ninguna';
    }
    return joke.flagses.map(flag => flag.flag).join(', ');
  }

  getType(joke: Jokes): string {
    return joke.types?.type || 'Desconocido';
  }

  getCategory(joke: Jokes): string {
    return joke.categories?.category || 'Sin categoría';
  }

  openPrimeraVezPage(): void {
    this.router.navigate(['/primera_vez']).then(() => {
      window.location.reload();
    });
  }

  applyFilter(): void {
    this.jokes.filter = this.searchText.trim().toLowerCase();
  }
}
