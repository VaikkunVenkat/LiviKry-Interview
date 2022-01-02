import React, { useState as useStateMock, useEffect as useEffectMock } from 'react';
import { shallow } from 'enzyme';

import Container from '../Container';

jest.mock('react', () => ({
  // @ts-ignore Spread types may only be created from object types.
  ...jest.requireActual('react'),
  useState: jest.fn(),
  useEffect: jest.fn(),
}));

describe('Container component', () => {
  const setServicesData = jest.fn();
  (useStateMock as jest.Mock<any>).mockReturnValueOnce([
    [],
    setServicesData,
  ]);
  (useEffectMock as jest.Mock<any>)
    .mockImplementationOnce(() => null);
  
  const refresh = false;
  const children = (props: any) => <div>children</div>

  const wrapper = shallow(<Container refresh={refresh}>{children}</Container>);
  
  it('renderes container component', () => {
    expect(wrapper.find('#container')).toHaveLength(1);
  });
  // TODO: Given more time I would extend the unit tests for this component to test each service function.
});