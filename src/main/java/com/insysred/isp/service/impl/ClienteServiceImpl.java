package com.insysred.isp.service.impl;

import com.insysred.isp.dto.ClienteContratoDTO;
import com.insysred.isp.dto.ClienteDto;
import com.insysred.isp.dto.ClienteSucursalDto;
import com.insysred.isp.entities.Canton;
import com.insysred.isp.entities.Cliente;
import com.insysred.isp.entities.Contrato;
import com.insysred.isp.entities.Provincia;
import com.insysred.isp.filtros.FiltroCliente;
import com.insysred.isp.mapper.ClienteMapper;
import com.insysred.isp.repository.CantonRepository;
import com.insysred.isp.repository.ClienteRepository;
import com.insysred.isp.repository.ContratoRepository;
import com.insysred.isp.repository.ProvinciaRepository;
import com.insysred.isp.service.declare.CLienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements CLienteService {

    private static Logger logger = Logger.getLogger(ClienteServiceImpl.class.getName());

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Value("${ruta.file.base}")
    private String rutaBase;
    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private ReferenciaServiceImpl referenciaServiceImpl;

    @Autowired
    private ProvinciaRepository provinciaRepository;
    @Autowired
    private CantonRepository cantonRepository;

    @Override
    public ClienteDto guardar(ClienteDto clienteDTO, Long idSucursal) throws Exception {
        // String localPath = "D:\\fileInsysred\\";
        ClienteDto clienteNew = new ClienteDto();
        //verificar cliente no exista
        if(clienteDTO.getIdentificacion() != null){
            try {
                List<Cliente> listaCli = clienteRepository.getClientesByIdentificacion(clienteDTO.getIdentificacion());
                if(listaCli.size() > 0){
                    throw new Exception("Cliente ya existe");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e);
            }
        }
        try {
            // Obtener el nombre del archivo
            // String fileName = file.getCedula().getOriginalFilename();

            // Crear la ruta completa del archivo en el sistema de archivos local
            //   Path filePath = Paths.get(localPath, fileName);

            // Guardar el archivo en el sistema de archivos local
            //  Files.write(filePath, file.getCedula().getBytes());
            /* ************************************ */
            Cliente cliente = clienteMapper.toEntity(clienteDTO);
            Cliente clienteGuardado = clienteRepository.save(cliente);
            clienteNew = clienteMapper.toDto(clienteGuardado);
            if(clienteDTO.getReferencia() != null) {
                clienteDTO.getReferencia().setCliente(clienteNew);
                referenciaServiceImpl.guardar(clienteDTO.getReferencia());
            }
            clienteRepository.guardarAsignacion(clienteGuardado.getId(), idSucursal);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return clienteNew;
    }

    public ClienteDto actualizar(Long id,ClienteDto clienteDTO){
        ClienteDto clienteNew = new ClienteDto();
        try {
           Optional<Cliente> cliente = clienteRepository.findById(id);
              if(cliente.isPresent()){
                cliente.get().setNombres(clienteDTO.getNombres() != null ? clienteDTO.getNombres() : cliente.get().getNombres());
                cliente.get().setApellidos(clienteDTO.getApellidos() != null ? clienteDTO.getApellidos() : cliente.get().getApellidos());
                cliente.get().setSexo(clienteDTO.getSexo() != null ? clienteDTO.getSexo() : cliente.get().getSexo());
                cliente.get().setEmail(clienteDTO.getEmail() != null ? clienteDTO.getEmail() : cliente.get().getEmail());
                cliente.get().setIdentificacion(clienteDTO.getIdentificacion() != null ? clienteDTO.getIdentificacion() : cliente.get().getIdentificacion());
                cliente.get().setTipoDocumento(clienteDTO.getTipoDocumento() != null ? clienteDTO.getTipoDocumento() : cliente.get().getTipoDocumento());
                cliente.get().setFilePlanilla(clienteDTO.getFilePlanilla() != null ? clienteDTO.getFilePlanilla() : cliente.get().getFilePlanilla());
                cliente.get().setRazonSocial(clienteDTO.getRazonSocial() != null ? clienteDTO.getRazonSocial() : cliente.get().getRazonSocial());
                cliente.get().setFechaNace(clienteDTO.getFechaNace() != null ? clienteDTO.getFechaNace() : cliente.get().getFechaNace());
                cliente.get().setTelfCelular(clienteDTO.getTelfCelular() != null ? clienteDTO.getTelfCelular() : cliente.get().getTelfCelular());
                cliente.get().setTelfFijo(clienteDTO.getTelfFijo() != null ? clienteDTO.getTelfFijo() : cliente.get().getTelfFijo());
                cliente.get().setUbicacion(clienteDTO.getUbicacion() != null ? clienteDTO.getUbicacion() : cliente.get().getUbicacion());
                cliente.get().setReferenciaUbicacion(clienteDTO.getReferenciaUbicacion() != null ? clienteDTO.getReferenciaUbicacion() : cliente.get().getReferenciaUbicacion());
                cliente.get().setLatitud(clienteDTO.getLatitud() != null ? clienteDTO.getLatitud() : cliente.get().getLatitud());
                cliente.get().setLongitud(clienteDTO.getLongitud() != null ? clienteDTO.getLongitud() : cliente.get().getLongitud());
                cliente.get().setProvincia(clienteDTO.getProvincia() != null ? provinciaRepository.findById(clienteDTO.getProvincia().getId()).get() : cliente.get().getProvincia());
                cliente.get().setCanton(clienteDTO.getCanton() != null ? cantonRepository.findById(clienteDTO.getCanton().getId()).get() : cliente.get().getCanton());
                  clienteNew = clienteMapper.toDto(clienteRepository.save(cliente.get()));
                    if(clienteDTO.getReferencia() != null) {
                        clienteDTO.getReferencia().setCliente(clienteNew);
                        referenciaServiceImpl.actualizar(clienteDTO.getReferencia().getId(),clienteDTO.getReferencia());
                    }
              }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clienteNew;
    }
    @Override
    public List<ClienteDto> listaClientes() {
        return clienteMapper.toDto(clienteRepository.findAll());
    }

    @Override
    public ClienteDto getById(Long idCLiente) {
        return clienteMapper.toDto(clienteRepository.getReferenceById(idCLiente));
    }

    @Override
    public Page<ClienteContratoDTO> obtenerClientes(PageRequest paginaRequest, String filtro, Long idSucursal) {
        try{
            logger.info("Sucursal: " + idSucursal);
            Page<Cliente> clientes = clienteRepository.findAll(FiltroCliente.contieneTexto(filtro, idSucursal), paginaRequest);
            logger.info("Total de clientes: " + clientes.getTotalElements());
            List<ClienteContratoDTO> resultado = new ArrayList<>();
            for (Cliente cliente : clientes) {
                logger.info(cliente.toString());
                List<Contrato> contrato = contratoRepository.getByIdClienteEstadoContratoValido(cliente.getId());
                if(contrato.size() <= 0) {
                    resultado.add(new ClienteContratoDTO(cliente, null, null, null, null));
                }else{
                    for (Contrato contrato1 : contrato) {
                        resultado.add(new ClienteContratoDTO(cliente, contrato1.getNumContrato(), contrato1.getEstadoContrato(), contrato1.getIp(), contrato1.getPlan().getName()));
                    }
                }
            }

            return new PageImpl<>(resultado, paginaRequest, clientes.getTotalElements());
        }catch (Exception e){
            e.printStackTrace();
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            return null;
        }
    }

    @Override
    public Boolean agregarSucursal(ClienteSucursalDto clienteSucursalDto) {
        Boolean agregaSucValidator = false;
        try {
            List<Object> listaUsuSuc = clienteRepository.getClienteBySucursal(clienteSucursalDto.getIdCliente(), clienteSucursalDto.getIdSucursal());
            System.out.println(listaUsuSuc.size());
            if(listaUsuSuc.size() > 0){
                agregaSucValidator = false;
            }else {
                System.out.println("dos");
                clienteRepository.guardarAsignacion(clienteSucursalDto.getIdCliente(), clienteSucursalDto.getIdSucursal());
                agregaSucValidator = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            agregaSucValidator = false;
        }
        return agregaSucValidator;
    }

    @Override
    public List<ClienteDto> getBySycursal(Long idSucursal) {
        return null;
    }

    @Override
    public Boolean verificarCliente(String idCliente) throws Exception {
        logger.info("Verificar cliente");
        logger.info("Identificacion: " + idCliente);
        Boolean validator = false;
        try {
            List<Cliente> listaCli = clienteRepository.getClientesByIdentificacion(idCliente);
            if(listaCli.size() > 0){
                validator = true;
            }else {
                validator = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return validator;
    }
}
