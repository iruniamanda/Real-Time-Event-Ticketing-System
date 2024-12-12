import { Component } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {NgForOf} from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-vendor',
  imports: [
    NgForOf,
    RouterLink
  ],
  templateUrl: './vendor.component.html',
  styleUrl: './vendor.component.css'
})
export class VendorComponent {
  logs: string[] = []; // Property to store logs
  constructor(private http: HttpClient) {
  }

  onStart() {
    const apiUrl = 'http://localhost:8080/ticket-system/vendor/start';

    this.http.post(apiUrl, {}).subscribe({
      next: (response) => {
        console.log('API call successful:', response);
        alert('Vendor started successfully!');
        this.onClick();
      },
      error: (error) => {
        console.error('API call failed:', error);
        alert('Failed to start the vendor. Please try again.');
      }
    });
  }

  onStop() {
    const apiUrl = ' http://localhost:8080/ticket-system/vendor/stop';

    this.http.post(apiUrl, {}).subscribe({
      next: (response) => {
        console.log('API call successful:', response);
        alert('Vendor Stopped successfully!');
      },
      error: (error) => {
        console.error('API call failed:', error);
        alert('Failed to Stop the vendor. Please try again.');
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
