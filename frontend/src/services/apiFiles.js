import axios from 'axios';

const apiFiles = axios.create({
  baseURL: 'http://localhost:8088/api/file-server/v1/files', 
});

/**
 * Faz o upload de um único arquivo para o ms-files
 * @param {File} file - O objeto do arquivo vindo do input
 * @returns {Promise<string>} - Retorna a URL da imagem salva
 */
export const uploadArquivo = async (file) => {
  const formData = new FormData();
  formData.append('file', file);

  try {
    const response = await apiFiles.post('/upload', formData);
    
    const fileId = response.data.data.id; 

    const urlParaSalvarNoSSB = `http://localhost:8088/api/file-server/v1/files/${fileId}`;

    console.log("URL gerada para o SSB:", urlParaSalvarNoSSB);
    
    return urlParaSalvarNoSSB; 

  } catch (error) {
    console.error("Erro no upload para o ms-files:", error);
    return null;
  }
};

export default apiFiles;