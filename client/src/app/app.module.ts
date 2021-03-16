import {HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatDialogModule} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatRadioModule} from '@angular/material/radio';
import {MatSelectModule} from '@angular/material/select';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatSliderModule} from '@angular/material/slider';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatSortModule} from '@angular/material/sort';
import {MatTableModule} from '@angular/material/table';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatTooltipModule} from '@angular/material/tooltip';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {InjectableRxStompConfig, RxStompService, rxStompServiceFactory} from '@stomp/ng2-stompjs';
import {AboutComponent} from './about/about.component';
import {AppRoutingModule} from './app-routing.module';
import {appRxStompConfig} from './app-rx-stomp.config';
import {AppComponent} from './app.component';
import {ConfirmDeleteDialogComponent} from './list-saved-scenes/confirm-delete-dialog.component';
import {SceneDetailsComponent} from './list-saved-scenes/scene-details.component';
import {SceneEditComponent} from './list-saved-scenes/scene-edit.component';
import {ScenesComponent} from './list-saved-scenes/scenes.component';
import {MenuComponent} from './menu/menu.component';
import {MonitorComponent} from './monitor/monitor.component';
import {ScenesService} from './scene/scenes.service';
import {SettingsComponent} from './settings/settings.component';
import {SimulatorControlComponent} from './simulator/simulator-control.component';
import {SimulatorLedComponent} from './simulator/simulator-led.component';
import {SimulatorComponent} from './simulator/simulator.component';

@NgModule({
  declarations: [
    AppComponent,
    MonitorComponent,
    MenuComponent,
    SimulatorComponent,
    SimulatorLedComponent,
    SimulatorControlComponent,
    AboutComponent,
    ScenesComponent,
    ConfirmDeleteDialogComponent,
    SceneEditComponent,
    SceneDetailsComponent,
    SettingsComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    AppRoutingModule,
    MatSlideToggleModule,
    MatFormFieldModule,
    MatSelectModule,
    MatCheckboxModule,
    MatRadioModule,
    MatSliderModule,
    MatPaginatorModule,
    MatTableModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatInputModule,
    FormsModule,
    MatTooltipModule,
    MatSnackBarModule,
    MatSortModule
  ],
  providers: [
    ScenesService,
    {
      provide: InjectableRxStompConfig,
      useValue: appRxStompConfig
    },
    /*  {
        provide: InjectableRxStompConfig,
        useClass: Stomp,
        deps: [SettingsService]
      },*/
    {
      provide: RxStompService,
      useFactory: rxStompServiceFactory,
      deps: [InjectableRxStompConfig]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
