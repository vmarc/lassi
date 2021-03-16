import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AboutComponent} from './about/about.component';
import {SceneDetailsComponent} from './list-saved-scenes/scene-details.component';
import {SceneEditComponent} from './list-saved-scenes/scene-edit.component';
import {ScenesComponent} from './list-saved-scenes/scenes.component';
import {MenuComponent} from './menu/menu.component';
import {MonitorComponent} from './monitor/monitor.component';
import {SettingsComponent} from './settings/settings.component';
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
