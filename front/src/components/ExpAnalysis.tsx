import React from "react";
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

ChartJS.register(
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend
);

const data = {
  labels: ["커밋", "이슈", "머지", "리뷰", "효율성", "보안성"],
  datasets: [
    {
      label: "경험치",
      data: [5, 7, 5, 9, 7, 4],
      backgroundColor: "rgba(127, 255, 212, 0.5)",
      borderColor: "aquamarine",
      pointBackgroundColor: "rgb(127, 255, 212)",
      borderWidth: 3,
      color: "black",
    },
  ],
};

export default function ExpAnalysis() {
  return (
    <Radar
      data={data}
      options={{
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
}
