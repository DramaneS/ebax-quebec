import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CustumerService } from '../service/custumer.service';

import { CustumerComponent } from './custumer.component';

describe('Custumer Management Component', () => {
  let comp: CustumerComponent;
  let fixture: ComponentFixture<CustumerComponent>;
  let service: CustumerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'custumer', component: CustumerComponent }]),
        HttpClientTestingModule,
        CustumerComponent,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              }),
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(CustumerComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CustumerComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CustumerService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        }),
      ),
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.custumers?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to custumerService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getCustumerIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getCustumerIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
