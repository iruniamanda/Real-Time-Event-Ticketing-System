import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import {FormsModule} from '@angular/forms';


@Component({
  selector: 'app-system-configuration',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './system-configuration.component.html',
  styleUrl: './system-configuration.component.css'
})
export class SystemConfigurationComponent {
  loginObj = {
    "totalTicket": "",
    "ticketReleaseRate": "",
    "customerRetrievalRate": "",
    "maxTicketCapacity": "",
    "vendorId": "",
    "vendorCount":"",
    "customerCount":"",
  };

  constructor(private http: HttpClient, private router: Router) {
  }

  onSubmit(): void {
    const apiUrl = 'http://localhost:8080/ticket-system/system_config';

    this.http.post(apiUrl, this.loginObj).pipe(
      tap((response: any) => {
        // Handle success response
        alert(response.message || 'System Configured Successfully!');
        // Navigate to vendor Dashboard page after successful configuration
        //this.router.navigate(['/vendor-dashboard']).then(r => console.log(r));
      }),
      catchError((error: any) => {
        let errorMessage = 'An unexpected error occurred.';

        // Handle validation errors
        if (error.error) {
          const validationErrors = error.error;
          errorMessage = Object.values(validationErrors).join('\n');
        }
        alert(errorMessage);
        return of(null);
      })
    ).subscribe();
  }
}
