import { HttpClient } from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import { of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import {FormsModule} from '@angular/forms';
import {NgIf} from '@angular/common';


@Component({
  selector: 'app-systemconfig',
  standalone: true,
  imports: [FormsModule, NgIf, RouterLink],
  templateUrl: './systemconfig.component.html',
  styleUrl: './systemconfig.component.css'
})
export class SystemconfigComponent  implements  OnInit {
  loginObj = {
    "totalTickets": "",
    "ticketReleaseRate": "",
    "customerRetrievalRate": "",
    "maxCapacity": "",
    "vendorId": "",
    "vendorCount":"",
    "customerCount":"",
  };

  isUpdating = false;


  constructor(private http: HttpClient, private router: Router) {
  }

  ngOnInit() {
    this.onFetch()
  }

  onFetch(): void {
    const apiUrl = 'http://localhost:8080/ticket-system/getSystemConfig';

    // Construct query parameters from the loginObj
    const params = new URLSearchParams({
      totalTickets: this.loginObj.totalTickets,
      ticketReleaseRate: this.loginObj.ticketReleaseRate,
      customerRetrievalRate: this.loginObj.customerRetrievalRate,
      maxCapacity: this.loginObj.maxCapacity,
      vendorId: this.loginObj.vendorId,
      vendorCount:this.loginObj.vendorCount,
      customerCount:this.loginObj.customerCount
    }).toString();

    // Call the GET API with query parameters
    this.http.get(`${apiUrl}?${params}`).pipe(
      tap((response: any) => {
        // Handle success response
        const data = {
          vendorCount: response?.[0].vendor_count,
          customerCount :response?.[0].customer_count,
          totalTickets:response?.[0].totalTickets,
          maxCapacity:response?.[0].maxCapacity,
          customerRetrievalRate: response?.[0].customerRetrievalRate,
          ticketReleaseRate:response?.[0].ticketReleaseRate,
          vendorId: response?.[0].vendorId,
        }
        this.loginObj = data
        console.log(response , "response body");
        alert(response.message || 'System Configured Successfully!');
        this.isUpdating = true;
        // Optionally navigate to another page
        // this.router.navigate(['/vendor-dashboard']).then(r => console.log(r));
      }),
      catchError((error: any) => {
        let errorMessage = 'An unexpected error occurred.';
        this.isUpdating = false;
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
  /*Save method*/
  onSubmit(): void {
    console.log("onSubmit");
    const apiUrl = 'http://localhost:8080/ticket-system/system_config';

    this.http.post(apiUrl, this.loginObj).pipe(
      tap((response: any) => {
        // Handle success response
        alert(response.message || 'System Configured Successfully!');
        this.onIntilization();
        this.onFetch()
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

  /*Update method*/
  onUpdate(): void {
    console.log("onupdate");
    const apiUrl = 'http://localhost:8080/ticket-system/updateSystemConfig';

    this.http.post(apiUrl, this.loginObj).pipe(
      tap((response: any) => {
        // Handle success response
        console.log(response , "update response")
        alert(response.message || 'SystemConfig Update Successfully!');
        this.onIntilization();
        this.onFetch()
        // Navigate to vendor Dashboard page after successful configuration
        //this.router.navigate(['/vendor-dashboard']).then(r => console.log(r));
      }),
      catchError((error: any) => {
        let errorMessage = 'An unexpected error occurred.';
          console.log(error , "update error")
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
  /*Update method*/
  onIntilization(): void {
    const apiUrl = 'http://localhost:8080/ticket-system/initialize';

    this.http.post(apiUrl, this.loginObj).pipe(
      tap((response: any) => {
        // Handle success response
        console.log("ressssss" , response)
        alert( 'System Configured Successfully!');
        // Navigate to vendor Dashboard page after successful configuration
        //this.router.navigate(['/vendor-dashboard']).then(r => console.log(r));
      }),
      catchError((error: any) => {
        let errorMessage = 'An unexpected error occurred.';
        console.log(error,"error")
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
