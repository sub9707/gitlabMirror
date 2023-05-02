import React from "react";

type propType = {
  title: string | undefined;
  desc: string | undefined;
  exp: number | undefined;
  rating: number | undefined;
  isActive: boolean | undefined;
};

function RepositoryCard(props: propType) {
  return (
    <div className="h-64 p-6 bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
      <div
        style={{ height: "10%", display: "flex", justifyContent: "flex-end" }}
      >
        <span className="mr-3 text-sm font-medium text-gray-900 dark:text-gray-300">
          레포지터리 공개
        </span>
        <label className="relative inline-flex items-center cursor-pointer">
          <input type="checkbox" value="" className="sr-only peer" />
          <div className="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-blue-800 rounded-full peer dark:bg-gray-700 peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[0px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-blue-600"></div>
        </label>
      </div>
      <div
        style={{
          display: "flex",
          justifyContent: "space-around",
          alignItems: "center",
          height: "80%",
        }}
      >
        <div
          style={{
            width: "40%",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            border: "1px solid black",
            height: "100%",
          }}
        >
          {props.isActive ? "캐릭터" : "달걀"}
        </div>
        <div style={{ width: "50%" }}>
          <p
            style={{
              fontSize: "2em",
              fontWeight: "600",
              overflow: "hidden",
              whiteSpace: "nowrap",
              textOverflow: "ellipsis",
            }}
          >
            {props.title}
          </p>
          <p style={{ fontSize: "1em", fontWeight: "500", marginBlock: "3%" }}>
            {props.desc === null ? "설명 없음" : props.desc}
          </p>
          <div>
            <p>경험치 : {props.exp}</p>
            <p>배틀 레이팅 : {props.rating} </p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default RepositoryCard;
