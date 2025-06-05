import { TestBed } from '@angular/core/testing';

import { PhimServiceService } from './phim-service.service';

describe('PhimServiceService', () => {
  let service: PhimServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PhimServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
