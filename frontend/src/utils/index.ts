export const isValidURL = (url: string): boolean => {
  var pattern = new RegExp('^(https?:\\/\\/)?'+ // protocol
    '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|'+ // domain name
    '((\\d{1,3}\\.){3}\\d{1,3}))'+ // OR ip (v4) address
    '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*'+ // port and path
    '(\\?[;&a-z\\d%_.~+=-]*)?'+ // query string
    '(\\#[-a-z\\d_]*)?$','i'); // fragment locator
  return pattern.test(url);
}

export const filterArrObjects = (arr: any[], obj: any, property: string): any[] => {
  return arr.filter((arrObj) => arrObj[property] !== obj?.[property])
}

export const emptyStringValidation = (input: string): boolean => {
  return input !== '' && input !== undefined;
};

export const API_URL = 'http://localhost:9090/api/services';

export const APP_REFRESH_URL = 'http://localhost:9090/api/refreshServices';

export const DELAY_TIME = 60;