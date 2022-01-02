import { IServicesData } from "../../fixtures/types";

type createUpdateHandler = (newData: IServicesData, oldData?: IServicesData) => Promise<any>;
type deleteHandler = (oldData: IServicesData) => Promise<any>;

export interface IFunctionprops {
  createService: createUpdateHandler,
  updateService: createUpdateHandler,
  deleteService: deleteHandler,
  servicesData: IServicesData[],
}

export interface IContainerProps {
  children: (arg0: IFunctionprops) => React.ReactNode
  refresh: boolean
}
