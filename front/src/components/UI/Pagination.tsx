import React, { useState } from "react";
import "./Pagination.css";
import Pagination from "react-js-pagination";

type pageProps = {
  currentPage: number;
  setCurrentPage: React.Dispatch<React.SetStateAction<number>>;
  totalPage: number;
  totalElement: number;
};
const Paging = (props: pageProps) => {
  const [page, setPage] = useState(props.currentPage);

  const handlePageChange = (page: number) => {
    props.setCurrentPage(page);
    setPage(page);
    console.log("page changed to ", page);
  };

  return (
    <Pagination
      activePage={page}
      itemsCountPerPage={6}
      totalItemsCount={props.totalElement}
      pageRangeDisplayed={5}
      prevPageText={"‹"}
      nextPageText={"›"}
      onChange={handlePageChange}
    />
  );
};

export default Paging;
