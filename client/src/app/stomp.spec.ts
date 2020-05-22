import { Stomp } from './stomp';

describe('Stomp', () => {
  it('should create an instance', () => {
    expect(new Stomp(null)).toBeTruthy();
  });

});
