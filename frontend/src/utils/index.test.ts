import { isValidURL, filterArrObjects, emptyStringValidation } from ".";

describe('utility function tests', () => {
  describe('isValidURL', () => {
    it('valid1', () => {
      const output = isValidURL('https://api.service.com');
      expect(output).toBe(true);
    });
    it('valid2', () => {
      const output = isValidURL('http://www.boredapi.com/api');
      expect(output).toBe(true);
    });
    it('invalid1', () => {
      const output = isValidURL('uinbieanb');
      expect(output).toBe(false);
    });
    it('invalid2', () => {
      const output = isValidURL('http://localhost.c');
      expect(output).toBe(false);
    })
  });
  describe('filterArrObjects', () => {
    it('test', () => {
      const testArrObjects = [
        { name: 'name', value: 'value' },
        { name: 'lastName', value: 'lastValue' },
      ];
      const object = { name: 'name', value: 'value' };
      const output = filterArrObjects(testArrObjects, object, 'name');
      expect(output).toEqual([{ name: 'lastName', value: 'lastValue' }]);
    });
  });
  describe('emptyStringValidation', () => {
    it('empty string', () => {
      const output = emptyStringValidation('');
      expect(output).toBe(false);
    });
    it('non-empty string', () => {
      const output = emptyStringValidation('non-empty');
      expect(output).toBe(true);
    });
  });
});