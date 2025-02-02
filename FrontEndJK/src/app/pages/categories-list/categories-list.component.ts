import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Categories } from '../../model/categories.model';
import { CategoriesService } from '../../service/categories.service';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { CategoriesDialogComponent } from '../categories-dialog/categories-dialog.component';

@Component({
  selector: 'app-category-list',
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
  templateUrl: './categories-list.component.html',
  styleUrls: ['./categories-list.component.css']
})
export class CategoryListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'category', 'actions'];
  categoriesDataSource = new MatTableDataSource<Categories>();
  searchText = '';

  constructor(private categoryService: CategoriesService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.categoryService.getCategories().subscribe({
      next: (categories) => {
        this.categoriesDataSource.data = categories;
      },
      error: (err) => {
        console.error('Error al cargar las categorías:', err);
      }
    });
  }

  applyFilter(): void {
    this.categoriesDataSource.filter = this.searchText.trim().toLowerCase();
  }

  addCategory(): void {
    const dialogRef = this.dialog.open(CategoriesDialogComponent, {
      width: '400px',
      panelClass: 'custom-dialog-container',
      data: { id: null, category: '' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.categoryService.createCategory(result).subscribe(() => {
          this.loadCategories();
        });
      }
    });
  }

  editCategory(category: Categories): void {
    const dialogRef = this.dialog.open(CategoriesDialogComponent, {
      width: '400px',
      data: { ...category }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.categoryService.updateCategory(result).subscribe(() => {
          this.loadCategories();
        });
      }
    });
  }

  deleteCategory(categoryId: number): void {
    if (confirm(`¿Estás seguro de eliminar la categoría con ID ${categoryId}?`)) {
      this.categoryService.deleteCategory(categoryId).subscribe(() => {
        this.loadCategories();
      });
    }
  }

  openJokesPage(): void{
    window.history.back();
  }
}
