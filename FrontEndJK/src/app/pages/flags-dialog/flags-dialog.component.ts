import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Flags } from '../../model/flags.model';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-flags-dialog',
  standalone: true,
  imports: [MatFormFieldModule, FormsModule, MatButtonModule, CommonModule, MatInputModule],
  templateUrl: './flags-dialog.component.html',
  styleUrls: ['./flags-dialog.component.css' ]
})
export class FlagsDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<FlagsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Flags
  ) {}

  save(): void {
    if (!this.data.flag.trim()) {
      alert('El campo flag no puede estar vacío.');
      return;
    }
    this.dialogRef.close(this.data);
  }

  cancel(): void {
    this.dialogRef.close();
  }
}
