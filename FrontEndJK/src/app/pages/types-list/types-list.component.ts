import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Types } from '../../model/types.model';
import { TypesService } from '../../service/types.service';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { TypesDialogComponent } from '../types-dialog/types-dialog.component';

@Component({
  selector: 'app-types-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    FormsModule,
    RouterModule,
    MatDialogModule
  ],
  templateUrl: './types-list.component.html',
  styleUrls: ['./types-list.component.css']
})
export class TypesListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'type', 'actions'];
  typesDataSource = new MatTableDataSource<Types>();
  searchText = '';

  constructor(private typesService: TypesService, private router: Router, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadTypes();
  }

  loadTypes(): void {
    this.typesService.getTypes().subscribe({
      next: (types) => {
        this.typesDataSource.data = types;
      },
      error: (err) => {
        console.error('Error al cargar los tipos:', err);
      }
    });
  }

  applyFilter(): void {
    this.typesDataSource.filter = this.searchText.trim().toLowerCase();
  }

  addType(): void {
    const dialogRef = this.dialog.open(TypesDialogComponent, {
      width: '400px',
      panelClass: 'custom-dialog-container',
      data: { type: '' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.typesService.createType(result).subscribe(() => {
          this.loadTypes();
        });
      }
    });
  }

  editType(type: Types): void {
    const dialogRef = this.dialog.open(TypesDialogComponent, {
      width: '400px',
      data: { ...type }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.typesService.updateType(result).subscribe(() => {
          this.loadTypes();
        });
      }
    });
  }

  openJokesPage(): void {
    this.router.navigate(['/jokes']).then(() => {
      window.location.reload();
    });
  }

  deleteType(typeId: number): void {
    if (!typeId || typeId === undefined) {
      console.error('Error: El tipo no tiene un ID válido');
      return;
    }

    if (confirm(`¿Estás seguro de eliminar el tipo con ID ${typeId}?`)) {
      this.typesService.deleteType(typeId).subscribe(
        () => {
          console.log(`Tipo con ID ${typeId} eliminado`);
          this.loadTypes();
        },
        error => console.error('Error al eliminar el tipo:', error)
      );
    }
  }
}
