import { Component} from "@angular/core";
import { FormsModule } from "@angular/forms";

@Component({
  selector: "my-app",
  imports: [FormsModule],
  template:`<div>
      <input [(ngModel)]="num" type="number" />
      @if(num==5){
        <p>Переменная num равна 5</p>
      } @else if(num==6){
        <p>Переменная num равна 6</p>
      } @else {
        <p></p>
      }
    </div>`
})
export class AppComponent {
  num = 5;
}
