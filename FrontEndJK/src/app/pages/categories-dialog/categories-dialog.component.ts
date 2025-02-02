import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Categories } from '../../model/categories.model';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-category-dialog',
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule, CommonModule],
  templateUrl: './categories-dialog.component.html',
  styleUrls: ['./categories-dialog.component.css']
})
export class CategoriesDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<CategoriesDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Categories
  ) {}

  save(): void {
    if (!this.data.category.trim()) {
      alert('El campo categoría no puede estar vacío.');
      return;
    }
    this.dialogRef.close(this.data);
  }

  cancel(): void {
    this.dialogRef.close();
  }
}
