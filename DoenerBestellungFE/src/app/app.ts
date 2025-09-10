import { Component, signal, OnInit, inject} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Router, RouterOutlet} from '@angular/router';
@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: true,
  imports: [
    RouterOutlet,
  ],
  styleUrl: './app.scss'
})
export class App {

}

