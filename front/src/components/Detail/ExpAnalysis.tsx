"use client";

import React, { useState, useEffect } from "react";
import {
  Chart as ChartJS,
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend,
} from "chart.js";
import { Radar } from "react-chartjs-2";
import { ExpAnaysisChartType, GrowthFactorType } from "@/types/repoDetail";

ChartJS.register(
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend
);

const labelMap: { [key: string]: string } = {
  커밋: "COMMIT",
  머지: "MERGE",
  이슈: "ISSUE",
  리뷰: "REVIEW",
  보안성: "SECURITY",
  효율성: "EFFICIENCY",
};

const ExpAnalysis = ({ growthFactor }: { growthFactor: GrowthFactorType }) => {
  const labels = ["커밋", "머지", "이슈", "리뷰", "보안성", "효율성"];
  const [chartData, setChartData] = useState<ExpAnaysisChartType>({
    labels,
    datasets: [
      {
        label: "획득 경험치",
        data: [],
        backgroundColor: "rgba(127, 255, 212, 0.5)",
        borderColor: "aquamarine",
        pointBackgroundColor: "rgb(127, 255, 212)",
        borderWidth: 3,
        color: "black",
      },
    ],
  });

  useEffect(() => {
    const data: string[] = [];

    for (let i = 0; i < labels.length; i++) {
      const growthFactorValue = growthFactor[labelMap[labels[i]]];
      if (growthFactorValue) {
        data.push(growthFactorValue.toString());
      } else {
        data.push("0");
      }
    }

    setChartData((prevState) => ({
      ...prevState,
      datasets: [
        {
          ...prevState.datasets[0],
          data: data,
        },
      ],
    }));
  }, []);

  return (
    <Radar
      data={chartData}
      options={{
        onHover: () => {},
        maintainAspectRatio: false,
        scales: {
          r: {
            angleLines: {
              color: "rgb(210, 210, 210)",
            },
            grid: {
              color: "rgb(210, 210, 210)",
            },
            pointLabels: {
              font: {
                family: "SUIT-Thin",
                size: 16,
                weight: "bold",
              },
              color: "rgb(50, 50, 50)",
            },
            ticks: {
              color: "rgb(50, 50, 50)",
            },
          },
        },
        plugins: {
          legend: {
            onClick: () => {},
            position: "right",
            labels: {
              color: "rgb(50, 50, 50)",
              font: {
                family: "SUIT-Thin",
                size: 16,
                weight: "bold",
              },
            },
          },
        },
      }}
    />
  );
};

export default ExpAnalysis;
