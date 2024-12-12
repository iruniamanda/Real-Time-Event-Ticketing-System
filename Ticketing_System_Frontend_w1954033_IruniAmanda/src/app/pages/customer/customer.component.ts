import { Component } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {NgForOf} from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-customer',
  imports: [
    NgForOf,
    RouterLink
  ],
  templateUrl: './customer.component.html',
  styleUrl: './customer.component.css'
})
export class CustomerComponent {
  logs: string[] = []; // Property to store logs
  constructor(private http: HttpClient) {
  }

  onStart() {
    const apiUrl = 'http://localhost:8080/ticket-system/customer/start';

    this.http.post(apiUrl, {}).subscribe({
      next: (response) => {
        console.log('API call successful:', response);
        alert('Customer started successfully!');
        this.onClick();
      },
      error: (error) => {
        console.error('API call failed:', error);
        alert('Failed to start the Customer. Please try again.');
      }
    });
  }

  onStop() {
    const apiUrl = 'http://localhost:8080/ticket-system/customer/stop';

    this.http.post(apiUrl, {}).subscribe({
      next: (response) => {
        console.log('API call successful:', response);
        alert('Customer Stopped successfully!');
      },
      error: (error) => {
        console.error('API call failed:', error);
        alert('Failed to Stop the Customer. Please try again.');
      }
    });
  }

  onClick() {
    const apiUrl = ' http://localhost:8080/ticket-system/logs';

    this.http.get(apiUrl, {}).subscribe({
      next: (response) => {
        console.log('API call successful:', response);
        this.logs = response as string[]
        alert('Logs are here!');
      },
      error: (error) => {
        console.error('API call failed:', error);
        alert('Failed to generate logs Please try again.');
      }
    });
  }

}
