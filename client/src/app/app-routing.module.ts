import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AboutComponent} from './about/about.component';
import {SceneDetailsComponent} from './scene/scene-details.component';
import {SceneEditComponent} from './scene/scene-edit.component';
import {ScenesComponent} from './scene/scenes.component';
import {MenuComponent} from './menu/menu.component';
import {MonitorComponent} from './monitor/monitor.component';
import {SettingsComponent} from './settings/settings.component';
import {OldSimulatorComponent} from './old-simulator/old-simulator.component';
import {SimulatorComponent} from './simulator/simulator.component';

export const routes: Routes = [
  {
    path: 'scenes/:sceneId/edit',
    component: SceneEditComponent
  },
  {
    path: 'scenes/:sceneId',
    component: SceneDetailsComponent
  },
  {
    path: 'scenes',
    component: ScenesComponent
  },
  {
    path: 'simulator',
    component: SimulatorComponent
  },
  {
    path: 'old-simulator',
    component: OldSimulatorComponent
  },
  {
    path: 'monitor',
    component: MonitorComponent
  },
  {
    path: 'settings',
    component: SettingsComponent
  },
  {
    path: 'about',
    component: AboutComponent
  },
  {
    path: '',
    component: MenuComponent
  }

];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {enableTracing: false, relativeLinkResolution: 'legacy'})
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
