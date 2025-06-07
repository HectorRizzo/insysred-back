package com.insysred.isp.service.impl;

import com.insysred.isp.dto.ReferenciaDTO;
import com.insysred.isp.entities.Canton;
import com.insysred.isp.entities.Cliente;
import com.insysred.isp.entities.Provincia;
import com.insysred.isp.entities.Referencia;
import com.insysred.isp.mapper.ReferenciaMapper;
import com.insysred.isp.repository.CantonRepository;
import com.insysred.isp.repository.ClienteRepository;
import com.insysred.isp.repository.ProvinciaRepository;
import com.insysred.isp.repository.ReferenciaRepository;
import com.insysred.isp.service.declare.ReferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReferenciaServiceImpl implements ReferenciaService {

    @Autowired
    private ReferenciaRepository referenciaRepository;
    @Autowired
    private ReferenciaMapper referenciaMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private CantonRepository cantonRepository;


    @Override
    public ReferenciaDTO guardar(ReferenciaDTO referenciaDTO) throws Exception {
        ReferenciaDTO referenciaNew = new ReferenciaDTO();
        try{
            Referencia referencia = referenciaMapper.toEntity(referenciaDTO);
            referenciaNew = referenciaMapper.toDto(referenciaRepository.save(referencia));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return referenciaNew;
    }

    @Override
    public ReferenciaDTO actualizar(Long id, ReferenciaDTO referenciaDTO) {
        ReferenciaDTO referenciaNew = new ReferenciaDTO();
        try{
            Optional<Cliente> cliente = clienteRepository.findById(referenciaDTO.getCliente().getId());
            Optional<Provincia> provincia = provinciaRepository.findById(referenciaDTO.getProvincia().getId());
            Optional<Canton> canton = cantonRepository.findById(referenciaDTO.getCanton().getId());
            if(id != null){
            Optional<Referencia> referencia = referenciaRepository.findById(id);
                if(referencia.isPresent()) {
                    referencia.get().setNombres(referenciaDTO.getNombres() != null ? referenciaDTO.getNombres() : referencia.get().getNombres());
                    referencia.get().setApellidos(referenciaDTO.getApellidos() != null ? referenciaDTO.getApellidos() : referencia.get().getApellidos());
                    referencia.get().setParentesco(referenciaDTO.getParentesco() != null ? referenciaDTO.getParentesco() : referencia.get().getParentesco());
                    referencia.get().setTelfFijo(referenciaDTO.getTelfFijo() != null ? referenciaDTO.getTelfFijo() : referencia.get().getTelfFijo());
                    referencia.get().setTelfMovil(referenciaDTO.getTelfMovil() != null ? referenciaDTO.getTelfMovil() : referencia.get().getTelfMovil());
                    referencia.get().setDireccion(referenciaDTO.getDireccion() != null ? referenciaDTO.getDireccion() : referencia.get().getDireccion());
                    referencia.get().setCliente(cliente.isPresent() ? cliente.get() : referencia.get().getCliente());
                    referencia.get().setProvincia(provincia.isPresent() ? provincia.get() : referencia.get().getProvincia());
                    referencia.get().setCanton(canton.isPresent() ? canton.get() : referencia.get().getCanton());
                    referenciaNew = referenciaMapper.toDto(referenciaRepository.save(referencia.get()));
                }
            }else{
                referenciaNew = guardar(referenciaDTO);
            }
        }catch (Exception e){
            e.printStackTrace();
            ResponseEntity.status(500).build();
        }
        return referenciaNew;
    }

    @Override
    public ReferenciaDTO getReferenciaById(Long id) throws Exception {
        ReferenciaDTO referenciaDTO = new ReferenciaDTO();
        try{
            Optional<Referencia> referencia = referenciaRepository.findById(id);
            if(referencia.isPresent()){
                referenciaDTO = referenciaMapper.toDto(referencia.get());
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return referenciaDTO;
    }

    @Override
    public ReferenciaDTO getReferenciaByIdCliente(Long id) throws Exception {
        ReferenciaDTO referenciaDTO = new ReferenciaDTO();
        try{
            Optional<Referencia> referencia = referenciaRepository.getByCliente(id);
            if(referencia.isPresent()){
                referenciaDTO = referenciaMapper.toDto(referencia.get());
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return referenciaDTO;
    }

}
