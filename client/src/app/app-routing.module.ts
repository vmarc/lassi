import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MenuComponent} from './menu/menu.component';
import {TimeComponent} from './time/time.component';
import {MonitorComponent} from './monitor/monitor.component';
import {SetupComponent} from './setup/setup.component';
import {SimulatorComponent} from './simulator/simulator.component';
import {AboutComponent} from './about/about.component';
import { ListSavedScenesComponent } from './list-saved-scenes/list-saved-scenes.component';

export const routes: Routes = [
  {
    path: 'time',
    component: TimeComponent
  },
  {
    path: 'monitor',
    component: MonitorComponent
  },
  {
    path: 'setup',
    component: SetupComponent
  },
  {
    path: 'simulator',
    component: SimulatorComponent
  },
  {
    path: 'about',
    component: AboutComponent
  },
  {
    path: 'sceneslist',
    component: ListSavedScenesComponent
  },
  {
    path: '',
    component: MenuComponent
  }

];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {enableTracing: false})
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
