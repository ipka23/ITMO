import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';

// @ts-ignore
@Component({
  selector: "app-coordinates-form",
  imports: [FormsModule],
  template:
    `
      <span>Выберите X:</span>
      <label>
        <select [(ngModel)]="xSelect">
          <option value="-2">-2</option>
          <option value="-1.5">-1.5</option>
          <option value="-1">-1</option>
          <option value="-0.5">-0.5</option>
          <option value="0">0</option>
          <option value="0.5">0.5</option>
          <option value="1">1</option>
          <option value="1.5">1.5</option>
          <option value="2">2</option>
        </select>
      </label>
      <br>
      @if (xSelect === "" && buttonClicked) {
        <span>Выберите X!</span>
      }
      @else if (+xSelect < -2 && +xSelect > 2 && buttonClicked) {
        <span>X должен быть от -2 до 2!</span>
      }
      <span>Введите Y[-3;5]:</span>
      <label><input [(ngModel)]="yInput" type="number" step="any"></label>
      <br>
      @if (yInput === "" && buttonClicked) {
        <span>Выберите Y!</span>
      }
      @else if (+yInput < -3 && +yInput > 5 && buttonClicked) {
        <span>Y должен быть от -3 до 5!</span>
      }
      <span>Выберите R:</span>
      <label>
        <select [(ngModel)]="rSelect">
          <option value="-2">-2</option>
          <option value="-1.5">-1.5</option>
          <option value="-1">-1</option>
          <option value="-0.5">-0.5</option>
          <option value="0" selected>0</option>
          <option value="0.5">0.5</option>
          <option value="1">1</option>
          <option value="1.5">1.5</option>
          <option value="2">2</option>
        </select>
        <br>
        @if (rSelect === "" && buttonClicked) {
          <span>Выберите радиус R!</span>
        }
        @else if (+rSelect < 0 && buttonClicked) {
          <span>R должен быть от 0 до 2!</span>
        }
      </label>
      <br>
      <label>
        <button type="submit" (click)="submitForm()">Отправить</button>
      </label>
    `
})
export class CoordinatesFormComponent {
  xSelect = ""
  yInput = ""
  rSelect = ""
  buttonClicked: boolean = false
  submitForm(): void {
    this.buttonClicked = true
    setTimeout(() => {
      this.buttonClicked = false
    }, 3000)
  }
}
