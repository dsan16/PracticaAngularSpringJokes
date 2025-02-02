import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Types } from '../../model/types.model';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-types-dialog',
  standalone: true,
  imports: [MatFormFieldModule, FormsModule, MatButtonModule, CommonModule],
  templateUrl: './types-dialog.component.html',
  styleUrls: ['./types-dialog.component.css']
})
export class TypesDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<TypesDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Types
  ) {}

  save(): void {
    if (!this.data.type.trim()) {
      alert('El campo tipo no puede estar vacío.');
      return;
    }
    this.dialogRef.close(this.data);
  }

  cancel(): void {
    this.dialogRef.close();
  }
}
