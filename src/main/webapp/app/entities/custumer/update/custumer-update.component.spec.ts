import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IService } from 'app/entities/service/service.model';
import { ServiceService } from 'app/entities/service/service/service.service';
import { CustumerService } from '../service/custumer.service';
import { ICustumer } from '../custumer.model';
import { CustumerFormService } from './custumer-form.service';

import { CustumerUpdateComponent } from './custumer-update.component';

describe('Custumer Management Update Component', () => {
  let comp: CustumerUpdateComponent;
  let fixture: ComponentFixture<CustumerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let custumerFormService: CustumerFormService;
  let custumerService: CustumerService;
  let serviceService: ServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CustumerUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CustumerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CustumerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    custumerFormService = TestBed.inject(CustumerFormService);
    custumerService = TestBed.inject(CustumerService);
    serviceService = TestBed.inject(ServiceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Service query and add missing value', () => {
      const custumer: ICustumer = { id: 456 };
      const service: IService = { id: 25600 };
      custumer.service = service;

      const serviceCollection: IService[] = [{ id: 12365 }];
      jest.spyOn(serviceService, 'query').mockReturnValue(of(new HttpResponse({ body: serviceCollection })));
      const additionalServices = [service];
      const expectedCollection: IService[] = [...additionalServices, ...serviceCollection];
      jest.spyOn(serviceService, 'addServiceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ custumer });
      comp.ngOnInit();

      expect(serviceService.query).toHaveBeenCalled();
      expect(serviceService.addServiceToCollectionIfMissing).toHaveBeenCalledWith(
        serviceCollection,
        ...additionalServices.map(expect.objectContaining),
      );
      expect(comp.servicesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const custumer: ICustumer = { id: 456 };
      const service: IService = { id: 3503 };
      custumer.service = service;

      activatedRoute.data = of({ custumer });
      comp.ngOnInit();

      expect(comp.servicesSharedCollection).toContain(service);
      expect(comp.custumer).toEqual(custumer);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICustumer>>();
      const custumer = { id: 123 };
      jest.spyOn(custumerFormService, 'getCustumer').mockReturnValue(custumer);
      jest.spyOn(custumerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ custumer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: custumer }));
      saveSubject.complete();

      // THEN
      expect(custumerFormService.getCustumer).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(custumerService.update).toHaveBeenCalledWith(expect.objectContaining(custumer));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICustumer>>();
      const custumer = { id: 123 };
      jest.spyOn(custumerFormService, 'getCustumer').mockReturnValue({ id: null });
      jest.spyOn(custumerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ custumer: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: custumer }));
      saveSubject.complete();

      // THEN
      expect(custumerFormService.getCustumer).toHaveBeenCalled();
      expect(custumerService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICustumer>>();
      const custumer = { id: 123 };
      jest.spyOn(custumerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ custumer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(custumerService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareService', () => {
      it('Should forward to serviceService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(serviceService, 'compareService');
        comp.compareService(entity, entity2);
        expect(serviceService.compareService).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
