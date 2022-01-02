export interface IServicesData {
  date: Date,
  name: string,
  url: string,
  status: string,
}

export interface IColumns {
  title: string,
  field: string,
  type: 'string' | 'date',
  dateSetting?: { format: string },
  validate?: (rowData: IServicesData) => boolean,
  editable?: 'always' | 'onUpdate' | 'onAdd' | 'never',
  tooltip?: string,
  initialEditValue?: any,
}