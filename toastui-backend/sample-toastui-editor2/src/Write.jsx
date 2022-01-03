import React, { useRef, useEffect } from "react";
import axios from "axios";

// Toast UI Editor 모듈 추가
import { Editor } from "@toast-ui/react-editor";
import "@toast-ui/editor/dist/toastui-editor.css";

// SyntaxHighlight 모듈 추가
import codeSyntaxHighlight from "@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight-all.js";

// prism 모듈 추가
import "prismjs/themes/prism.css";

const Write = () => {
  const editorRef = useRef();

  const handleClick = () => {
    console.log(editorRef.current.getInstance().getMarkdown());
  };

  useEffect(() => {
    if (editorRef.current) {
      editorRef.current.getInstance().removeHook("addImageBlobHook");
      editorRef.current
        .getInstance()
        .addHook("addImageBlobHook", (blob, callback) => {
          (async () => {
            let formData = new FormData();
            formData.append("file", blob);

            console.log("이미지가 업로드 됐습니다.");

            const { data: filename } = await axios.post(
              "/file/upload",
              formData,
              {
                header: { "content-type": "multipart/formdata" },
              }
            );
            // .then((response) => {
            //   console.log(response);
            // });

            const imageUrl = "http://localhost:8080/file/upload/" + filename;

            callback(imageUrl, "iamge");
          })();

          return false;
        });
    }

    return () => {};
  }, [editorRef]);

  return (
    <div>
      <Editor
        initialValue="hello react editor world!"
        previewStyle="vertical"
        height="800px"
        initialEditType="markdown"
        useCommandShortcut={true}
        ref={editorRef}
        plugins={[codeSyntaxHighlight]}
      />
      <button onClick={handleClick}>Markdown 반환하기</button>
    </div>
  );
};

export default Write;
