import {RouterModule, Routes} from '@angular/router';
import {SystemConfigurationComponent} from './pages/system-configuration/system-configuration.component';
import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CustomerComponent} from './pages/customer/customer.component';
import {SystemconfigComponent} from './pages/systemconfig/systemconfig.component';
import {VendorComponent} from './pages/vendor/vendor.component';

export const routes: Routes = [


{
  path:'system_configuration',
    component:SystemConfigurationComponent
},
  {
    path:'systemconfig',
    component:SystemconfigComponent
  },
  {
    path:'customer',
    component:CustomerComponent
  },
  {
    path:'vendor',
    component:VendorComponent
  },
  ];
@NgModule({
  imports: [RouterModule.forRoot(routes)], // Configure routes
  exports: [RouterModule]                  // Export RouterModule for use in the app
})
@NgModule({
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule, // Add this module
  ],
  providers: [],
})

export class AppRoutingModule {}
