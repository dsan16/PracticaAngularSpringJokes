import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { PrimeraVez } from '../../model/primeraVez.model';
import { PrimeraVezService } from '../../service/primeraVez.service';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-primera-vez-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatIconModule, MatButtonModule, CommonModule, FormsModule, RouterModule],
  templateUrl: './primera-vez-list.component.html',
  styleUrls: ['./primera-vez-list.component.css']
})
export class PrimeraVezListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'programa', 'fechaEmision', 'joke', 'telefonos', 'actions'];
  primeraVezDataSource = new MatTableDataSource<PrimeraVez>();
  searchText: string = '';

  constructor(private primeraVezService: PrimeraVezService, private router: Router) {}

  ngOnInit(): void {
    this.loadPrimeraVez();
  }

  loadPrimeraVez(): void {
    this.primeraVezService.getAll().subscribe(primeraVezList => {
      this.primeraVezDataSource.data = primeraVezList;
    });
  }

  applyFilter(): void {
    this.primeraVezDataSource.filter = this.searchText.trim().toLowerCase();
  }

  getTelefonos(primera: PrimeraVez): string {
    if (!primera.telefonos || primera.telefonos.length === 0) {
      return 'Ninguno';
    }
    return primera.telefonos.map(tel => tel.numero).join('<br>');
  }

  addPrimeraVez(): void {
    this.router.navigate(['/primera_vez/create']).then(() => {
      window.location.reload();
    });
  }

  editPrimeraVez(primeraVez: PrimeraVez): void {
    this.router.navigate([`/primera_vez/edit/${primeraVez.id}`]).then(() => {
      window.location.reload();
    });
  }

  deletePrimeraVez(id: number): void {
    if (confirm(`¿Estás seguro de eliminar la entrada con ID ${id}?`)) {
      this.primeraVezService.delete(id).subscribe(() => {
        this.loadPrimeraVez();
      });
    }
  }

  openJokesPage(): void {
    this.router.navigate(['/jokes']);
  }
}
