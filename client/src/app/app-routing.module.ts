import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MenuComponent} from './menu/menu.component';
import {MonitorComponent} from './monitor/monitor.component';
import {SimulatorComponent} from './simulator/simulator.component';
import {AboutComponent} from './about/about.component';
import { ListSavedScenesComponent } from './list-saved-scenes/list-saved-scenes.component';
import { SettingsComponent } from './settings/settings.component';

export const routes: Routes = [
  {
    path: 'settings',
    component: SettingsComponent

  },
  {
    path: 'monitor',
    component: MonitorComponent
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
    RouterModule.forRoot(routes, { enableTracing: false, relativeLinkResolution: 'legacy' })
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
