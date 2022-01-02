import React from "react";

type TypeHandler = (input: string) => Promise<any>;

interface IContainerProps {
  children: (createService: TypeHandler, updateService: TypeHandler, deleteService: TypeHandler) => React.ReactNode; 
}

const Container: React.FC<IContainerProps> = ({ children }) => {
  const createService = (input: string): Promise<any> => {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        console.log(`Action input: ${input}`)
        /* setData([...data, newData]); */
        resolve('');
      }, 1000);
    })
  }

  const updateService = (input: string): Promise<any> => {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        console.log(`Action input: ${input}`)
        /* setData([...data, newData]); */
        resolve('');
      }, 1000);
    })
  }

  const deleteService = (input: string): Promise<any> => {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        console.log(`Action input: ${input}`)
        /* setData([...data, newData]); */
        resolve('');
      }, 1000);
    })
  }

  return (
    <div>
      {children(createService, updateService, deleteService)}
    </div>
  );
};

export default Container;
