import { TestBed, async } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { AuthenticateComponent } from './components/authenticate/authenticate.component';
import { ForbiddenComponent } from './components/forbidden/forbidden.component';
import { HomeComponent } from './components/home/home.component';
import { BlockUIModule } from 'ng-block-ui';
import { SharedModule } from './shared/shared.module';
import { BrowserModule } from '@angular/platform-browser';
import { BiometricsModule } from './biometrics/biometrics.module';
import { ReportsModule } from './reports/reports.module';
import { AppRouting } from './app.routing';
describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        HomeComponent,
        ForbiddenComponent,
        AuthenticateComponent
      ],
      imports: [
        BlockUIModule.forRoot(),
        BrowserModule,
        SharedModule,
        BiometricsModule,
        ReportsModule,
        AppRouting
      ],
    }).compileComponents();
  }));
  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
  it(`should have as title 'app'`, async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('app');
  }));
});
