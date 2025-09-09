import { Component, signal, OnInit, inject} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Router, RouterOutlet} from '@angular/router';
import { finalize } from 'rxjs/operators';
@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: true,
  imports: [
    RouterOutlet
  ],
  styleUrl: './app.scss'
})
export class App {

  protected readonly title = signal('DoenerBestellungFE');

  private http= inject(HttpClient);
  private router= inject(Router);


}

