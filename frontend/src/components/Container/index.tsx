import React, { useState, useEffect } from "react";
import { IServicesData } from "../../fixtures/types";
import { servicesData as data } from "../../fixtures/services";
import { API_URL, APP_REFRESH_URL } from "../../utils";
import { IContainerProps } from "./types";

const Container: React.FC<IContainerProps> = ({ children, refresh }) => {

  useEffect(() => {
    fetch(APP_REFRESH_URL).then(res => res.json()).then((data: IServicesData[]) => setServicesData(data)).catch(error => console.error(error.message));
  }, [refresh]) 

  const [servicesData, setServicesData] = useState<IServicesData[]>(data);

  const createService = (newData: IServicesData): Promise<any> => {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        console.log(`Create input: ${JSON.stringify(newData)}`)
        fetch(API_URL, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          // TODO: pass in servicesData to POST Body
          body: JSON.stringify({})
      })
        /* setData([...data, newData]); */
        resolve('');
      }, 1000);
    })
  }

  const updateService = (newData: IServicesData, oldData?: IServicesData): Promise<any> => {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        console.log(`Update input: ${newData}`)
        /* setData([...data, newData]); */
        resolve('');
      }, 1000);
    })
  }

  const deleteService = (oldData: IServicesData): Promise<any> => {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        console.log(`Delete input: ${oldData}`)
        /* setData([...data, newData]); */
        resolve('');
      }, 1000);
    })
  }

  return (
    <div>
      {children({ createService, updateService, deleteService, servicesData })}
    </div>
  );
};

export default Container;
