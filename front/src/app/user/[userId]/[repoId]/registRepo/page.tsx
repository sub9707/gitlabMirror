"use client";
import { useState, useEffect, useRef } from "react";
import { NextPage } from "next";
import styles from "./page.module.scss";
import Image from "next/image";
import { PageProps, Todo } from "types/repoRegist";

const Page: NextPage<PageProps> = ({ params }) => {
  const [selected, setSelected] = useState<number | null>(null);
  const [mounted, setMounted] = useState<boolean>(false);
  const dice = useRef<HTMLImageElement>(null);
  const diceShadow = useRef<HTMLDivElement>(null);

  // 부화알 이미지 경로 배열
  const images = [
    "/static/images/monEgg1.png",
    "/static/images/monEgg2.png",
    "/static/images/monEgg3.png",
  ];

  // 부화알 선택 state
  const handleSelect = (index: number) => {
    if (selected === index) {
      setSelected(null);
    } else {
      setSelected(index);
    }
  };
  const [numArr, setNumArr] = useState([0, 0, 0, 0, 0]);

  function generateRandomNumArr() {
    let sum = 0;
    const newArr = [];

    // 첫번째 요소는 1~10사이의 랜덤값으로 설정
    newArr.push(Math.floor(Math.random() * 10) + 1);

    // 나머지 요소는 1~10사이의 랜덤값으로 설정되며, 총합이 30이 되도록 함
    for (let i = 1; i < 5; i++) {
      const maxNum = 30 - sum - (5 - i);
      const randomNum = Math.floor(Math.random() * Math.min(maxNum, 10)) + 1;
      newArr.push(randomNum);
      sum += randomNum;
    }

    setNumArr(newArr);
  }
  // 주사위 클릭 시 실행
  const diceClick = () => {
    const audio = new Audio("/static/sound/Dice.mp3");
    const randomNumArr = numArr.map(() => Math.floor(Math.random() * 10) + 1);
    setNumArr(randomNumArr);
    generateRandomNumArr();

    if (dice.current && diceShadow.current) {
      dice.current.style.transform = `translate(-50%, -250%) rotate(720deg)`;
      dice.current.style.filter = `blur(0.1em)`;
      diceShadow.current.style.transform = "translate(-50%, -50%) scaleX(0.7)";

      setTimeout(() => {
        if (dice.current && diceShadow.current) {
          dice.current.style.transform =
            "translate(-50%, -50%) rotate(-1440deg)";
          dice.current.style.filter = `blur(0em)`;
          diceShadow.current.style.transform =
            "translate(-50%, -50%) scaleX(1)";
        }
        audio.play();
      }, 200);
    }
  };

  useEffect(() => {
    setMounted(true);
  }, []);

  // 컨벤션 리스트
  const [todos, setTodos] = useState<Todo[]>([]);
  const [title, setTitle] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [input, setInput] = useState({ title: "", description: "" });
  const [addClicked, setAddClicked] = useState<boolean>(true);

  const handleAddTodo = (): void => {
    const newTodo: Todo = {
      id: todos.length + 1,
      title: title,
      description: description,
    };
    setTodos([...todos, newTodo]);
    setTitle("");
    setDescription("");
    setAddClicked(true);
  };

  const handleEditTodo = (
    id: number,
    title: string,
    description: string
  ): void => {
    const editedTodos: Todo[] = todos.map((todo) => {
      if (todo.id === id) {
        return {
          id: id,
          title: title,
          description: description,
        };
      } else {
        return todo;
      }
    });
    setTodos(editedTodos);
  };

  const handleDeleteTodo = (id: number): void => {
    const filteredTodos: Todo[] = todos.filter((todo) => todo.id !== id);
    setTodos(filteredTodos);
  };
  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setInput((prevInput) => ({ ...prevInput, [name]: value }));
  };

  if (!mounted) return null;

  return (
    <div className={styles.pageContainer}>
      <div className={styles.pageBox}>
        <div className={styles.monSelect}>
          <p style={{ color: "aliceblue", fontSize: "3rem" }}>함뽑아바라!</p>
          <div style={{ display: "flex", justifyContent: "center" }}>
            {images.map((src, index) => (
              <div
                key={index}
                className={`${styles.monFrame} ${
                  selected === index ? styles.selected : ""
                }`}
                onClick={() => handleSelect(index)}
              >
                <Image
                  src={src}
                  alt={`mon${index + 1}`}
                  width={500}
                  height={400}
                  className={styles.monEgg}
                />
              </div>
            ))}
          </div>
        </div>
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            marginTop: "5%",
          }}
        >
          <div className={styles.leftBox}>
            <p className={styles.commitTitle}>커밋 컨벤션 입력</p>
            <div
              style={{
                display: "flex",
                alignItems: "center",
                flexDirection: "column",
              }}
            >
              {addClicked ? (
                <div
                  style={{
                    marginBottom: "5%",
                    width: "100%",
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    paddingInline: "3%",
                    fontSize: "0.8vw",
                  }}
                >
                  <label
                    style={{
                      color: "black",
                      marginInline: "3%",
                      width: "20%",
                    }}
                  >
                    <p style={{ color: "white" }}>커밋 메시지 컨벤션</p>
                    <input
                      type="text"
                      name="title"
                      className={styles.inputFields}
                      value={title}
                      onChange={(e) => setTitle(e.target.value)}
                      placeholder="커밋 컨벤션 입력"
                    />
                  </label>
                  <label
                    style={{
                      color: "black",
                      marginInline: "3%",
                      width: "70%",
                      marginTop: "0.6%",
                    }}
                  >
                    <p style={{ color: "white" }}>설명 </p>
                    <textarea
                      name="description"
                      value={description}
                      onChange={(e) => setDescription(e.target.value)}
                      className={styles.inputFields}
                      style={{
                        color: "black",
                        width: "100%",
                      }}
                    />
                  </label>
                  <button
                    style={{
                      width: "10%",
                      height: "4em",
                      border: "1px solid white",
                    }}
                    onClick={handleAddTodo}
                    className={styles.buttonInput}
                  >
                    추가
                  </button>
                </div>
              ) : (
                <button onClick={() => setAddClicked(true)}>+</button>
              )}
              <div
                style={{
                  display: "flex",
                  flexDirection: "column",
                  alignItems: "center",
                }}
              >
                {todos.map((todo) => (
                  <div key={todo.id} className={styles.commitBox}>
                    <div>
                      <label htmlFor={`title-${todo.id}`}>컨벤션 명:</label>
                      <input
                        type="text"
                        id={`title-${todo.id}`}
                        defaultValue={todo.title}
                        className={styles.inputFields}
                        style={{
                          color: "black",
                          marginBottom: "3%",
                        }}
                        onBlur={(e) =>
                          handleEditTodo(
                            todo.id,
                            e.target.value,
                            todo.description
                          )
                        }
                        onChange={handleInputChange}
                      />
                    </div>
                    <div>
                      <label htmlFor={`description-${todo.id}`}>설명:</label>
                      <input
                        type="text"
                        id={`description-${todo.id}`}
                        defaultValue={todo.description}
                        className={styles.inputFields}
                        style={{
                          color: "white",
                          marginBottom: "3%",
                        }}
                        onBlur={(e) =>
                          handleEditTodo(todo.id, todo.title, e.target.value)
                        }
                        onChange={handleInputChange}
                      />
                    </div>
                    <button onClick={() => handleDeleteTodo(todo.id)}>
                      Delete
                    </button>
                  </div>
                ))}
              </div>
            </div>
          </div>
          <div className={styles.rigthBox}>
            <div className={styles.diceContainer}>
              <p className={styles.diceTitle}>초기 능력치 설정</p>
              <div className={styles.diceBox}>
                <Image
                  src="/static/images/dice.png"
                  alt="dice"
                  width={100}
                  height={100}
                  className={styles[`dice`]}
                  onClick={diceClick}
                  ref={dice}
                />
                <div className={styles.diceShadow} ref={diceShadow} />
              </div>
              <div className={styles.statusBox}>
                <div className="flex flex-col" style={{ width: "80%" }}>
                  <div className="overflow-x-auto sm:-mx-6 lg:-mx-8">
                    <div className="inline-block min-w-full py-2 sm:px-6 lg:px-8">
                      <div className="overflow-hidden">
                        <table className="min-w-full border text-center dark:border-neutral-500">
                          <tbody
                            style={{ textAlign: "center" }}
                            id={styles.tableBody}
                          >
                            <tr
                              className="border-white dark:border-neutral-500"
                              style={{ borderWidth: "5px" }}
                            >
                              <td
                                className=" border-white whitespace-nowrap border-r px-6 py-4  dark:border-neutral-500"
                                style={{ width: "50%", borderWidth: "5px" }}
                              >
                                공격력
                              </td>
                              <td
                                id={styles.ranNumber}
                                className="whitespace-nowrap border-r px-6 py-4 dark:border-neutral-500"
                              >
                                {numArr[0]}
                              </td>
                            </tr>
                            <tr
                              className="border-white dark:border-neutral-500"
                              style={{ borderWidth: "5px" }}
                            >
                              <td
                                className="border-white whitespace-nowrap border-r px-6 py-4  dark:border-neutral-500"
                                style={{ width: "50%", borderWidth: "5px" }}
                              >
                                회피율
                              </td>
                              <td className="whitespace-nowrap border-r px-6 py-4 dark:border-neutral-500">
                                {numArr[1]}%
                              </td>
                            </tr>
                            <tr
                              className="border-white dark:border-neutral-500"
                              style={{ borderWidth: "5px" }}
                            >
                              <td
                                className=" border-white whitespace-nowrap border-r px-6 py-4  dark:border-neutral-500"
                                style={{ width: "50%", borderWidth: "5px" }}
                              >
                                방어율
                              </td>
                              <td className="whitespace-nowrap border-r px-6 py-4 dark:border-neutral-500">
                                {numArr[2]}%
                              </td>
                            </tr>
                            <tr
                              className="border-white dark:border-neutral-500"
                              style={{ borderWidth: "5px" }}
                            >
                              <td
                                className="border-white whitespace-nowrap border-r px-6 py-4  dark:border-neutral-500"
                                style={{ width: "50%", borderWidth: "5px" }}
                              >
                                치명타율
                              </td>
                              <td className="whitespace-nowrap border-r px-6 py-4 dark:border-neutral-500">
                                {numArr[3]}%
                              </td>
                            </tr>
                            <tr
                              className="border-white dark:border-neutral-500"
                              style={{ borderWidth: "5px" }}
                            >
                              <td
                                className="border-white whitespace-nowrap border-r px-6 py-4  dark:border-neutral-500"
                                style={{ width: "50%", borderWidth: "5px" }}
                              >
                                명중률
                              </td>
                              <td className="whitespace-nowrap border-r px-6 py-4 dark:border-neutral-500">
                                {numArr[4]}%
                              </td>
                            </tr>
                          </tbody>
                        </table>
                        <div>
                          <p>(공격력 최소 1 ~ 최대 10)</p>
                          <p>
                            (기타 확률 최소 1% ~ 최대 10%, 모든 확률 총합은 30
                            이하)
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div style={{ display: "flex", marginBottom: "3%" }}>
          <button
            style={{
              width: "10em",
              height: "3em",
              fontSize: "1.2em",
              marginInline: "2%",
            }}
            className={styles.buttonAccept}
          >
            결 정
          </button>
          <button
            style={{
              width: "10em",
              height: "3em",
              fontSize: "1.2em",
              marginInline: "2%",
            }}
            className={styles.buttonDeny}
          >
            취 소
          </button>
        </div>
      </div>
    </div>
  );
};

export default Page;
