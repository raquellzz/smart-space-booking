import axios from 'axios';

const BASE_URL = import.meta.env.VITE_API_FILES_URL; 

const apiFiles = axios.create({
  baseURL: BASE_URL,
});

export const uploadArquivo = async (file) => {
  const formData = new FormData();
  formData.append('file', file);

  try {
    const response = await apiFiles.post('/upload', formData);
    return response.data.data.id; 
  } catch (error) {
    console.error("Erro no upload:", error);
    return null;
  }
};

export default apiFiles;