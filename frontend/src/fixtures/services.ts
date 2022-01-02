import { emptyStringValidation, isValidURL } from '../utils';
import { IServicesData, IColumns } from './types';

// initiating empty table if database empty
export const servicesData: IServicesData[] = [];

// initiating columns of table
export const servicesColums: IColumns[] = [
  { title: 'Creation time', field: 'created_at', type: 'date', dateSetting: { format: 'dd/MM/yyy HH:mm:ss' }, editable: 'never', initialEditValue: '',  tooltip: 'Date that api service was added' },
  { title: 'Name', field: 'name', type: 'string', validate: (rowData: IServicesData) => emptyStringValidation(rowData.name), tooltip: 'Name of the invoice' },
  { title: 'URL', field: 'url', type: 'string', validate: (rowData: IServicesData) => isValidURL(rowData.url), tooltip: 'API service url' },
  { title: 'Status', field: 'status', type: 'string', editable: 'never', initialEditValue: '' } // to be calculated on creation.
]