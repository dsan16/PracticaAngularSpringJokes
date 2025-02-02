import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Flags } from '../../model/flags.model';
import { FlagsService } from '../../service/flags.service';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { FlagsDialogComponent } from '../flags-dialog/flags-dialog.component';

@Component({
  selector: 'app-flags-list',
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
  templateUrl: './flags-list.component.html',
  styleUrls: ['./flags-list.component.css']
})
export class FlagsListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'flag', 'actions'];
  flagsDataSource = new MatTableDataSource<Flags>();
  searchText = '';

  constructor(private flagsService: FlagsService, private router: Router, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadFlags();
  }

  loadFlags(): void {
    this.flagsService.getFlags().subscribe({
      next: (flags) => {
        this.flagsDataSource.data = flags;
      },
      error: (err) => {
        console.error('Error al cargar las flags:', err);
      }
    });
  }

  applyFilter(): void {
    this.flagsDataSource.filter = this.searchText.trim().toLowerCase();
  }

  addFlag(): void {
    const dialogRef = this.dialog.open(FlagsDialogComponent, {
      width: '400px',
      panelClass: 'custom-dialog-container',
      data: { flag: '' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.flagsService.createFlag(result).subscribe(() => {
          this.loadFlags();
        });
      }
    });
  }

  editFlag(flag: Flags): void {
    const dialogRef = this.dialog.open(FlagsDialogComponent, {
      width: '400px',
      data: { ...flag }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.flagsService.updateFlag(result).subscribe(() => {
          this.loadFlags();
        });
      }
    });
  }

  openJokesPage(): void {
    this.router.navigate(['/jokes']).then(() => {
      window.location.reload();
    });
  }

  deleteFlag(flagId: number): void {
    if (!flagId || flagId === undefined) {
      console.error('Error: La flag no tiene un ID válido');
      return;
    }

    if (confirm(`¿Estás seguro de eliminar la flag con ID ${flagId}?`)) {
      this.flagsService.deleteFlag(flagId).subscribe(
        () => {
          console.log(`Flag con ID ${flagId} eliminada`);
          this.loadFlags();
        },
        error => console.error('Error al eliminar flag:', error)
      );
    }
  }
}
