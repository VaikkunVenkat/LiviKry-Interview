
import React from 'react';
import { shallow } from "enzyme";
import MaterialTable from 'material-table';

import Table from './index'
import { IServicesData } from '../../fixtures/types';
import { newService, oldService, servicesColums } from '../../fixtures/services';

describe('Table component', () => {
  const servicesData: IServicesData[] = [];
  const createService = jest.fn();
  const updateService = jest.fn();
  const deleteService = jest.fn();
  const props = { createService, updateService, deleteService, servicesData };
  const wrapper = shallow(<Table {...props} />);
  const materialTableWrapper = wrapper.find(MaterialTable)
  it('expect to render MaterialTable component', () => {
    expect(materialTableWrapper).toHaveLength(1);
  });
  it('renders expected columns and data props', () => {
    expect(materialTableWrapper.prop('title')).toEqual('Services');
    expect(materialTableWrapper.prop('columns')).toEqual(servicesColums);
    expect(materialTableWrapper.prop('data')).toEqual(servicesData);
  });
  const editableProp = materialTableWrapper.prop('editable');
  it('handleAddRow', () => {
    editableProp.onRowAdd(newService);
    expect(createService).toHaveBeenCalledWith(newService);
  })
  it('handleDeleteRow', async () => {
    editableProp.onRowDelete(newService);
    expect(deleteService).toHaveBeenCalledWith(newService);
  });
  it('handleUpdate', () => {
    editableProp.onRowUpdate(newService, oldService);
    expect(updateService).toHaveBeenCalledWith(newService, oldService);
  })
});